package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

fun LazyGridScope.productionCompanySection(
    companies: List<ProductionCompanyUi>,
    modifier: Modifier = Modifier
) {
    if (companies.isNotEmpty()) {
        items(companies){ company ->
            CompanyProductionCard(
                imageUrl = company.logoUrl,
                companyName = company.name,
                countryName = company.originCountry,
                modifier = Modifier.padding(8.dp).width(160.dp).height(145.dp)
            )
        }
    } else {
        item (span = {GridItemSpan(maxLineSpan)}){
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                AppText(
                    text = stringResource(R.string.there_is_no_production_company),
                    style = Theme.textStyle.label.large,
                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                )
            }
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
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
            ) {
                productionCompanySection(
                    companies = fakeCompanies,
                    modifier = Modifier
                        .height(400.dp)
                )
            }
        }
    }
}

