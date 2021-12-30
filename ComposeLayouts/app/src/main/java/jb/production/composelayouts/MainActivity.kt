package jb.production.composelayouts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import jb.production.composelayouts.ui.theme.ComposeLayoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLayoutsTheme {
                MyApp()
            }
        }
    }
}

enum class Tab(val vector: ImageVector, val title: String) {
    ITEM1(Icons.Filled.Favorite, "Item 1"),
    ITEM2(Icons.Filled.Face, "Item 2")
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val onTabSelect = { index: Int ->
        val item = Tab.values()[index]
        Toast.makeText(context, "${item.title} clicked", Toast.LENGTH_SHORT).show()
        when (index) {
            Tab.ITEM1.ordinal -> {
                // todo
            }
            Tab.ITEM2.ordinal -> {
                // todo
            }
        }
    }

    Scaffold(
        topBar = { AppHeader() },
        bottomBar = { AppBottomBar(onTabSelect = onTabSelect) },
    ) { innerPadding ->
        AppBody(modifier.padding(innerPadding))
    }

}

@Composable
fun AppBottomBar(items: List<Tab> = listOf(Tab.ITEM1, Tab.ITEM2), onTabSelect: (Int) -> Unit) {
    var indexSelected by remember { mutableStateOf(0) }

    BottomNavigation {
        items.forEachIndexed { index, s ->
            BottomNavigationItem(
                icon = {
                    Icon(s.vector, contentDescription = s.title)
                },
                label = {
                    Text(text = if (index == indexSelected) s.title else "")
                },
                alwaysShowLabel = false,
                selected = (indexSelected == index),
                onClick = {
                    indexSelected = index
                    onTabSelect(indexSelected)
                },
            )
        }
    }
}


@Composable
fun AppBody(modifier: Modifier = Modifier) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Title("Custom Layout Sample")
            CustomLayoutSample()

            Title("Constraint Layout Sample")
            ConstraintLayoutSample()

            Title("Intrinsics Sample")
            IntrinsicsSample()
        }
    }
}

@Composable
private fun IntrinsicsSample() {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "Left",
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
        )
        Divider(color = Color.Black, modifier = Modifier
            .fillMaxHeight()
            .width(2.dp))
        Text(
            text = "Right",
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Composable
private fun CustomLayoutSample() {
    val topics = listOf(
        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
        "Religion", "Social sciences", "Technology", "TV", "Writing"
    )

    ComposeLayoutsTheme {
        Row(Modifier.horizontalScroll(rememberScrollState())) {
            CustomStaggeredGrid(noOfRows = 4) {
                topics.map { CustomChip(Modifier.padding(8.dp), name = it) }
            }
        }
    }
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
        ),
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
private fun ConstraintLayoutSample() {
    ConstraintLayout {
        val (hText1, vText1, hText2, vText2) = createRefs()
        val barrier = createEndBarrier(hText1, hText2, margin = 12.dp)
        val guidelineStart = createGuidelineFromStart(fraction = 0.1f)
        val guidelineEnd = createGuidelineFromEnd(fraction = 0.1f)
        val guidelineTop = createGuidelineFromTop(fraction = 0.1f)
        val marginTop = 8.dp
        Text(
            text = "Name",
            modifier = Modifier.constrainAs(hText1) {
                top.linkTo(guidelineTop)
                start.linkTo(guidelineStart)
            }
        )
        Text(
            text = "Mobile",
            modifier = Modifier.constrainAs(hText2) {
                top.linkTo(hText1.bottom, marginTop)
                start.linkTo(guidelineStart)
            }
        )

        Text(
            text = "Jems Bond",
            modifier = Modifier.constrainAs(vText1) {
                start.linkTo(barrier)
                top.linkTo(guidelineTop) // or top.linkTo(parent.top)
                end.linkTo(guidelineEnd)
            }
        )
        Text(
            text = "1234567809",
            modifier = Modifier.constrainAs(vText2) {
                top.linkTo(vText1.bottom, marginTop)
                start.linkTo(barrier)
                end.linkTo(guidelineEnd)
            }
        )
    }
}

@Composable
fun CustomStaggeredGrid(
    modifier: Modifier = Modifier,
    noOfRows: Int = 3,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        val rowTotalWidths = IntArray(noOfRows) { 0 }
        val rowMaxHeights = IntArray(noOfRows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->

            val placeable = measurable.measure(constraints)
            val row = index % noOfRows

            rowTotalWidths[row] += placeable.width
            rowMaxHeights[row] = Math.max(rowMaxHeights[row], placeable.height)

            placeable
        }

        val gridWidth =
            rowTotalWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth
        val gridHeight =
            rowMaxHeights.sum().coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowX = IntArray(noOfRows) { 0 }
        val rowY = IntArray(noOfRows) { 0 }
        for (i in 1 until noOfRows) {
            rowY[i] = rowY[i - 1] + rowMaxHeights[i - 1]
        }

        layout(gridWidth, gridHeight) {
            placeables.forEachIndexed { index, placeable ->
                val row = index % noOfRows
                placeable.placeRelative(rowX[row], rowY[row])

                rowX[row] += placeable.width
            }
        }

    }
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]

        val placeableY = firstBaselineToTop.roundToPx() - firstBaseLine
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map {
            it.measure(constraints)
        }

        var yCors = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.place(0, yCors)
                yCors += placeable.height
            }
        }
    }
}

@Composable
fun CustomChip(modifier: Modifier = Modifier, name: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(width = Dp.Hairline, color = Color.Black),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp, 14.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = name)
        }
    }
}

@Composable
fun AppHeader() {
    TopAppBar(title = {
        Text(text = "Heading")
    })
}

@Preview(name = "LightMode", showBackground = true)
@Composable
fun DefaultAppPreview() {
    ComposeLayoutsTheme {
        MyApp()
    }
}

//@Preview(name = "with Custom Modifier")
//@Composable
//fun CustomModifierPreview() {
//    ComposeLayoutsTheme {
//        Text("Hi There!", Modifier.firstBaselineToTop(32.dp))
//    }
//}

//@Preview(name = "with Default Modifier")
//@Composable
//fun DefaultModifierPreview() {
//    ComposeLayoutsTheme {
//        Text("Hi There!", Modifier.padding(top = 32.dp))
//    }
//}

//@Preview(name = "Custom Staggered Grid", showBackground = true)
//@Composable
//fun DefaultCustomStaggeredGrid() {
//
//    val topics = listOf(
//        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
//        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
//        "Religion", "Social sciences", "Technology", "TV", "Writing"
//    )
//
//    ComposeLayoutsTheme {
//        Row(Modifier.horizontalScroll(rememberScrollState())) {
//            CustomStaggeredGrid(noOfRows = 4) {
//                topics.map { CustomChip(Modifier.padding(8.dp), name = it) }
//            }
//        }
//    }
//}


//@Preview(name = "Custom Layout", showBackground = true)
//@Composable
//fun DefaultCustomLayoutPreview(){
//    ComposeLayoutsTheme {
//        CustomColumn(Modifier.padding(all = 8.dp)){
//            Text("Jems")
//            Text("Bond")
//            Text("How Are you?")
//            Text("I am Fine, Thank you.")
//        }
//    }
//}