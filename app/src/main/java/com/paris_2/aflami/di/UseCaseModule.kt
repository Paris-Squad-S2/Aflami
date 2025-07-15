package com.paris_2.aflami.di

import com.domain.search.useCases.AutoCompleteCountryUseCase
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
import com.domain.search.useCases.FilterByListOfCategoriesUseCase
import com.domain.search.useCases.FilterMediaByRatingUseCase
import com.domain.search.useCases.GetAllCategoriesUseCase
import com.domain.search.useCases.GetAllRecentSearchesUseCase
import com.domain.search.useCases.GetCountryCodeByNameUseCase
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCases.SearchByQueryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAllRecentSearchesUseCase(get()) }
    factory { ClearRecentSearchUseCase(get()) }
    factory { ClearAllRecentSearchesUseCase(get()) }
    factory { AutoCompleteCountryUseCase(get()) }
    factory { GetCountryCodeByNameUseCase(get()) }
    factory { FilterByListOfCategoriesUseCase() }
    factory { FilterMediaByRatingUseCase() }
    factory { GetAllCategoriesUseCase(get()) }
    factory { GetMediaByActorNameUseCase(get()) }
    factory { GetMoviesOnlyByCountryNameUseCase(get()) }
    factory { SearchByQueryUseCase(get()) }
}

