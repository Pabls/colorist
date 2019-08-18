package ru.ar4i.colorist.application.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import com.google.gson.Gson
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.ar4i.colorist.data.repositories.colors.ColorsRepository
import ru.ar4i.colorist.data.repositories.colors.IColorsRepository
import ru.ar4i.colorist.data.repositories.resourses.IResoursesRepository
import ru.ar4i.colorist.data.repositories.resourses.ResoursesRepository
import ru.ar4i.colorist.data.repositories.state.IStateRepository
import ru.ar4i.colorist.data.repositories.state.StateRepository
import ru.ar4i.colorist.domain.colors.ColorsInteractor
import ru.ar4i.colorist.domain.colors.IColorsInteractor
import ru.ar4i.colorist.domain.resourses.IResoursesInteractor
import ru.ar4i.colorist.domain.resourses.ResoursesInteractor
import ru.ar4i.colorist.domain.state.IStateInteractor
import ru.ar4i.colorist.domain.state.StateInteractor
import ru.ar4i.colorist.presentation.camera.presenter.CameraPresenter
import ru.ar4i.colorist.presentation.color_matching.presenter.ColorMatchingPresenter
import ru.ar4i.colorist.presentation.colors.presenter.ColorsPresenter
import ru.ar4i.colorist.presentation.main.presenter.MainPresenter

object AppModule {

    val appModule = module {
        single { Gson() }
    }

    val repositoriesModule = module {
        single { ColorsRepository(get(), get()) } bind IColorsRepository::class
        single { StateRepository(get(), get()) } bind IStateRepository::class
        single { ResoursesRepository(get()) } bind IResoursesRepository::class
    }

    val interactorsModule = module {
        single { ColorsInteractor(get()) } bind IColorsInteractor::class
        single { StateInteractor(get()) } bind IStateInteractor::class
        single { ResoursesInteractor(get()) } bind IResoursesInteractor::class
    }

    val presentationModule = module {
        factory { MainPresenter(get()) }
        factory { ColorsPresenter(get()) }
        factory { CameraPresenter() }
        factory { ColorMatchingPresenter(get(), get()) }
    }
}