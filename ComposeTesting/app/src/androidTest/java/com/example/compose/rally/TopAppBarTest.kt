package com.example.compose.rally

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpTopAppBar() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
    }

    @Test
    fun rallyTopAppBarTest() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {

        // to debug (see semantic tree)
        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule
            .onRoot(true)
            .printToLog("currentLabelExists")

        // method 1
//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name.uppercase())
//            .assertExists()

        // method 2
        composeTestRule
            .onNode(
                matcher = hasText(RallyScreen.Accounts.name.uppercase())
                    .and(
                        hasParent(hasContentDescription(RallyScreen.Accounts.name))
                    ), useUnmergedTree = true
            )
            .assertExists()
    }
}