package com.example.common.middlewares

import com.example.common.state.AppState
import org.aaronwtlu.project.Klog
import org.reduxkotlin.Middleware

val loggingMiddleware: Middleware<AppState> = { store ->
    { next ->
        { action ->
            Klog.i("***************************************")
            Klog.i("Action: ${action::class.simpleName}")
            Klog.i("***************************************")
            next(action)
        }
    }

}