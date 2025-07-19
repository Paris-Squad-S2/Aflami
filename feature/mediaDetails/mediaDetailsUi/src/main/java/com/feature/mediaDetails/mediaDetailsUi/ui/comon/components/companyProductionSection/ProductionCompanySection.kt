package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun ProductionCompanySection(
    companies: List<ProductionCompanyUi>?,
    modifier: Modifier = Modifier
) {
    if (!companies.isNullOrEmpty()) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            companies.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { company ->
                        CompanyProductionCard(
                            imageUrl = company.logoUrl,
                            companyName = company.name,
                            countryName = company.originCountry,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    } else {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.there_is_no_production_company),
                style = Theme.textStyle.label.large,
                color = Theme.colors.text.body.copy(alpha = 0.6f)
            )
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewProductionCompanySection() {
    AflamiTheme {
        val fakeCompanies = listOf(
            ProductionCompanyUi(
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/5/5a/Castle_Rock_Entertainment.svg/1200px-Castle_Rock_Entertainment.svg.png",
                name = "Universal",
                originCountry = "US"
            ),
            ProductionCompanyUi(
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/4/4f/Paramount_Pictures_2022_logo.svg",
                name = "Paramount Pictures",
                originCountry = "US"
            ),
            ProductionCompanyUi(
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/a/af/20th_Century_Studios_Logo.svg",
                name = "20th Century Studios",
                originCountry = "US"
            ),
            ProductionCompanyUi(
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/a/af/20th_Century_Studios_Logo.svg",
                name = "20th Century Studios",
                originCountry = "US"
            ),
            ProductionCompanyUi(
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/a/af/20th_Century_Studios_Logo.svg",
                name = "20th Century Studios",
                originCountry = "US"
            )
        )
        BasePreview {
            ProductionCompanySection(
                companies = fakeCompanies,
                modifier = Modifier
                    .height(400.dp)
            )
        }
    }
}

