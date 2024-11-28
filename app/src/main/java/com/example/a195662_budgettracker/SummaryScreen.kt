package com.example.a195662_budgettracker.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a195662_budgettracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    budget: Double,
    totalExpenses: Double,
    onBackClick: () -> Unit
) {
    val remainingBudget = budget - totalExpenses

    // Define custom fonts
    val fontOne = FontFamily(Font(R.font.chango_regular, weight = FontWeight.Normal))
    val fontTwo = FontFamily(Font(R.font.montserrat_bold, weight = FontWeight.Normal))

    // Background image resource
    val backgroundPainter = painterResource(R.drawable.bg)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Summary",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                        fontFamily = fontTwo,
                        color = Color.DarkGray
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, // Default back arrow
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF2DDE2))
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(innerPadding)
            ) {
                // Background Image
                Image(
                    painter = backgroundPainter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Centered Card
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(width = 320.dp, height = 200.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Summary Title
                            Text(
                                text = "Summary",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 27.sp),
                                fontFamily = fontOne,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.size(30.dp))

                            // Total Expenses
                            Text(
                                text = stringResource(R.string.total_expenses, totalExpenses),
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                                fontFamily = fontTwo,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.size(8.dp))

                            // Remaining Budget
                            Text(
                                text = stringResource(R.string.remaining_budget, remainingBudget),
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                                fontFamily = fontTwo,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun SummaryScreenPreview() {
    SummaryScreen(
        budget = 1000.0,
        totalExpenses = 450.0,
        onBackClick = { /* No action for preview */ }
    )
}
