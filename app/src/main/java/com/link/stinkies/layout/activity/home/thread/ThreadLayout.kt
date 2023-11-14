@file:OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterialApi::class
)

package com.link.stinkies.layout.activity.home.thread

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import com.link.stinkies.layout.activity.home.thread.post.Post
import com.link.stinkies.ui.theme.background

@Composable
fun ThreadLayout(viewModel: HomeActivityVM, threadId: Int) {
    val thread = viewModel.thread.observeAsState()
    val isRefreshing = viewModel.threadLoading.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        isRefreshing.value == true,
        {
            viewModel.refreshThread(threadId)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(
                start = 8.dp,
                end = 8.dp,
            )
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            content = {
                items(thread.value?.posts?.count() ?: 0) { index ->
                    Post(
                        post = thread.value?.posts?.get(index),
                        modifier = if(index == (thread.value?.posts?.size?.minus(1)))
                            Modifier.padding(bottom = 8.dp)
                        else
                            Modifier
                    )
                }
            },
            modifier = Modifier
                .fillMaxHeight()
        )
        PullRefreshIndicator(
            isRefreshing.value == true,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}