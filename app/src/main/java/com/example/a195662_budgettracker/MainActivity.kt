/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.a195662_budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.a195662_budgettracker.ui.theme.SummaryScreen
import com.example.a195662_budgettracker.ui.theme.TipTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavGraph(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetAppBar(
    title: String,
    canNavigateBack: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(
            text = title,
            style = MaterialTheme.typography.displayLarge,
            color = Color.DarkGray) },
        navigationIcon = {
            if (canNavigateBack && onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "tip_time_layout"
    ) {
        composable("tip_time_layout") {
            TipTimeLayout(
                onNavigateToSummary = { budget, totalExpenses ->
                    navController.navigate("summary_screen/$budget/$totalExpenses")
                }
            )
        }
        composable(
            "summary_screen/{budget}/{totalExpenses}",
            arguments = listOf(
                navArgument("budget") { type = NavType.FloatType },
                navArgument("totalExpenses") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val budget = backStackEntry.arguments?.getFloat("budget")?.toDouble() ?: 0.0
            val totalExpenses = backStackEntry.arguments?.getFloat("totalExpenses")?.toDouble() ?: 0.0
            SummaryScreen(
                budget = budget,
                totalExpenses = totalExpenses,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}



@Composable
fun TipTimeLayout(
    onNavigateToSummary: (Double, Double) -> Unit
) {
    var priceInput by remember { mutableStateOf("") }
    var budgetInput by remember { mutableStateOf("") }
    var categoryInput by remember { mutableStateOf("") }
    var isRecurring by remember { mutableStateOf(false) }

    val budget = budgetInput.toDoubleOrNull() ?: 0.0
    val price = priceInput.toDoubleOrNull() ?: 0.0
    val totalExpenses = price

    val fontOne = FontFamily(
        Font(R.font.chango_regular, weight = FontWeight.Normal)
    )

    val image = painterResource(R.drawable.budgeticon2)
    val backgroundPainter = painterResource(R.drawable.bg)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BudgetAppBar(title = "Budget Tracker")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Ensure visible background
    ) {
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 30.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BUDGET TRACKER",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 27.sp),
                fontFamily = fontOne
            )
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.9F
            )
            Spacer(modifier = Modifier.height(50.dp))

            EditTextField(
                label = R.string.starting_budget,
                leadingIcon = R.drawable.money,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = budgetInput,
                onValueChanged = { budgetInput = it },
                placeholderText = "Enter your starting budget"
            )
            EditTextField(
                label = R.string.price,
                leadingIcon = R.drawable.price,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = priceInput,
                onValueChanged = { priceInput = it },
                placeholderText = "Enter your item's price"
            )
            CategoryDropdownMenu(
                selectedCategory = categoryInput,
                onCategorySelected = { categoryInput = it }
            )

            RecurringExpenseRow(
                isRecurring = isRecurring,
                onRecurringChanged = { isRecurring = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Button(
                onClick = {
                    onNavigateToSummary(budget, totalExpenses)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Add Expense",
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 18.sp),
                )
            }
        }
    }
}}


@Composable
fun calculateRemainingBudget(budget: Double, price: Double): Double {
    return budget - price
}

@Composable
fun EditTextField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholderText: String
) {
    Text(
        text = stringResource(label),
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )

    TextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), contentDescription = null) },
        singleLine = true,
        onValueChange = onValueChanged,
        keyboardOptions = keyboardOptions,
        placeholder = { Text(placeholderText) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CategoryDropdownMenu(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        "Food",
        "Transport",
        "Education",
        "Entertainment",
        "Self-care",
        "Shopping",
        "Bills",
        "Others"
    )
    var expanded by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {

        Text(
            text = "Category",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select a category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),


                )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false // Close dropdown upon selection
                            keyboardController?.hide() // Hide the keyboard when an item is selected
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecurringExpenseRow(
    isRecurring: Boolean,
    onRecurringChanged: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.recurring_expense))
        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = isRecurring,
            onCheckedChange = onRecurringChanged
        )
    }
}
@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout(
            onNavigateToSummary = { _, _ -> }
        )
    }
}
