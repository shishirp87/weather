package com.shishir.weatherapp.ui.dashboard.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shishir.weatherapp.utils.DateUtil.toFormattedDay

@Composable
fun ForecastComponent(
    modifier: Modifier = Modifier,
    date: String,
    minTemp: String,
    maxTemp: String,
    condition: String
) {
    Card(
        modifier = modifier.padding(end = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = date.toFormattedDay().orEmpty(),
                style = MaterialTheme.typography.titleMedium
            )
            Row {
                Text(
                    text = "Max ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = maxTemp,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.width(4.dp))
            Row {
                Text(
                    text = "Min ",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = minTemp, style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = condition,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForecastComponentPreview() {
    ForecastComponent(
        date = "2023-10-07",
        minTemp = "12",
        maxTemp = "28",
        condition = "Rainy"
    )
}