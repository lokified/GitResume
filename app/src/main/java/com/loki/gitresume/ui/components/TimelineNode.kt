package com.loki.gitresume.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loki.gitresume.util.CircleParameters
import com.loki.gitresume.util.LineParameters

@Composable
fun TimelineNode(
    position: TimelineNodePosition,
    contentStartOffset: Dp = 32.dp,
    spacerBetweenNodes: Dp = 32.dp,
    circleParameters: CircleParameters,
    lineParameters: LineParameters? = null,
    content: @Composable BoxScope.(Modifier) -> Unit
) {

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 8.dp)
            .drawBehind {
                val circleRadiusInPx = circleParameters.radius.toPx()
                drawCircle(
                    color = circleParameters.backgroundColor,
                    radius = circleRadiusInPx,
                    center = Offset(circleRadiusInPx, circleRadiusInPx)
                )

                lineParameters?.let {
                    drawLine(
                        brush = lineParameters.brush,
                        start = Offset(x = circleRadiusInPx, y = circleRadiusInPx * 2),
                        end = Offset(x = circleRadiusInPx, y = this.size.height),
                        strokeWidth = lineParameters.strokeWidth.toPx()
                    )
                }
            }
    ) {
        content(
            Modifier.padding(
                start = contentStartOffset,
                bottom = if (position != TimelineNodePosition.LAST) spacerBetweenNodes
                else 0.dp
            )
        )
    }
}

@Composable
fun ContentBubble(
    modifier: Modifier = Modifier,
    startDate: String,
    endDate: String,
    title: String,
    company: String,
    description: String
) {

    Column(
        modifier = modifier
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            val color = MaterialTheme.colorScheme.primary
            Text(text = startDate, color = color)
            Text(text = " - ", color = color)
            Text(text = endDate, color = color)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = company, fontSize = 16.sp, fontStyle = FontStyle.Italic)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = description)

    }
}

enum class TimelineNodePosition {
    FIRST,
    MIDDLE,
    LAST
}