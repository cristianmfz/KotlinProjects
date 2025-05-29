package com.android.borutoapp.ui.screen.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class SearchTopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun openSearchTopBar_addInputText_assertInputText() {
        val text = mutableStateOf("")
        composeTestRule.setContent {
            SearchTopBar(
                text = text.value,
                onTextChange = {
                    text.value = it
                },
                onCloseClicked = {},
                onSearchClicked = {}
            )
        }
        composeTestRule.onNode(hasText("Search here..."))
            .performTextInput("cristianmfz")
        composeTestRule.onNode(hasText("cristianmfz"))
            .assertExists()
    }

    @Test
    fun openSearchTopBar_addInputText_pressCloseButtonOnce_assertEmptyInputText() {
        val text = mutableStateOf("")
        composeTestRule.setContent {
            SearchTopBar(
                text = text.value,
                onTextChange = {
                    text.value = it
                },
                onCloseClicked = {},
                onSearchClicked = {}
            )
        }
        composeTestRule.onNode(hasText("Search here..."))
            .performTextInput("cristianmfz")
        composeTestRule.onNodeWithContentDescription("Close Icon")
            .performClick()
        composeTestRule.onNode(hasText("cristianmfz"))
            .assertDoesNotExist()
    }

    @Test
    fun openSearchTopBar_addInputText_pressCloseButtonTwice_assertClosedState() {
        val text = mutableStateOf("")
        val searchWidgetShown = mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown.value) {
                SearchTopBar(
                    text = text.value,
                    onTextChange = {
                        text.value = it
                    },
                    onCloseClicked = {
                        searchWidgetShown.value = false
                    },
                    onSearchClicked = {}
                )
            }
        }
        composeTestRule.onNode(hasText("Search here..."))
            .performTextInput("cristianmfz")
        composeTestRule.onNodeWithContentDescription("Close Icon")
            .performClick()
        composeTestRule.onNodeWithContentDescription("Close Icon")
            .performClick()
        composeTestRule.onNode(hasText("Search here..."))
            .assertDoesNotExist()
    }

    @Test
    fun openSearchTopBar_pressCloseButtonOnceWhenInputIsEmpty_assertClosedState() {
        val text = mutableStateOf("")
        val searchWidgetShown = mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown.value) {
                SearchTopBar(
                    text = text.value,
                    onTextChange = {
                        text.value = it
                    },
                    onCloseClicked = {
                        searchWidgetShown.value = false
                    },
                    onSearchClicked = {}
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Close Icon")
            .performClick()
        composeTestRule.onNode(hasText("Search here..."))
            .assertDoesNotExist()
    }

}