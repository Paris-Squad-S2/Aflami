package com.paris_2.aflami.designsystem.utils

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Preview(
    name = "Phone",
    group = "devices",
    device = "spec:width=360dp,height=640dp,dpi=480"
)
@Preview(
    name = "Arabic",
    locale = "ar",
    group = "devices",
    device = "spec:width=360dp,height=640dp,dpi=480"
)

@Preview(
    name = "Foldable",
    group = "devices",
    device = "spec:width=673dp,height=841dp,dpi=480"
)

@Preview(
    name = "Tablet",
    group = "devices",
    device = "spec:width=1280dp,height=800dp,dpi=480"
)

@PreviewLightDark
annotation class PreviewMultiDevices
