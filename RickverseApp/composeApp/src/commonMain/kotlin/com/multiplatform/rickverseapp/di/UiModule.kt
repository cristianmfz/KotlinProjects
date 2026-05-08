package com.multiplatform.rickverseapp.di

import com.multiplatform.rickverseapp.ui.detail.CharacterDetailViewModel
import com.multiplatform.rickverseapp.ui.home.tabs.characters.CharactersViewModel
import com.multiplatform.rickverseapp.ui.home.tabs.episodes.EpisodesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::EpisodesViewModel)
    viewModelOf(::CharactersViewModel)
    viewModelOf(::CharacterDetailViewModel)
}
