package jb.production.jetpackcomposetesting

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jb.production.jetpackcomposetesting.data.ConversationSample
import jb.production.jetpackcomposetesting.data.Message
import jb.production.jetpackcomposetesting.ui.theme.JetpackComposeTestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowMessageCard()
        }
    }

}

@Composable
fun ShowMessageCard() {
    JetpackComposeTestingTheme {
        Conversation(messages = ConversationSample.data)
    }
}


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message = message)
        }
    }
}

@Composable
fun MessageCard(message: Message) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(modifier = Modifier.padding(all = 4.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "launcher bg",
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.fillMaxWidth(1f)) {
            Text(
                text = message.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))

            val surfaceColor =
                if (expanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .animateContentSize()
                    .padding(1.dp),
                color = surfaceColor
            ) {
                Text(
                    text = message.body,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    style = MaterialTheme.typography.body2,
                    maxLines = if (!expanded) 1 else message.body.length / 2,
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun DefaultPreview() {
    ShowMessageCard()
}

//@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun DefaultNightPreview() {
//    ShowMessageCard()
//}