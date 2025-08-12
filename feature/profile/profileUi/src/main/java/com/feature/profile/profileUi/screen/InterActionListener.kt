package com.feature.profile.profileUi.screen

interface InterActionListener {
    fun onChooseLanguageClicked()
    fun onChooseAppearanceClicked()
    fun onSettingClicked()
    fun onLogoutClicked()
    fun onContentRestrictionClicked()
    fun onAppearanceApplyClicked(appearance: Appearance)
    fun onLanguageApplyClicked(language: Language)
    fun onLogoutApplyClicked()
    fun onDismissAppearanceDialog()
    fun onDismissLanguageDialog()
    fun onDismissSettingDialog()
    fun onDismissLogoutDialog()
    fun onDismissContentRestrictionDialog()
    fun onLanguageSelected(language: Language)
    fun onRestrictionSelected(contentRestriction: ContentRestriction)
    fun onWatchHistoryClicked()
    fun onMyRatingClicked()
}