# ⚡ flaremail-kt

[![Kotlin](https://img.shields.io/badge/kotlin-2.3.10-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/ktor-client-purple.svg?logo=ktor)](https://ktor.io/)

A lightweight Kotlin/Ktor client for the Cloudflare Worker Send Email API.

**flaremail-kt** provides a simple, coroutine-based, and type-safe way to dispatch emails through Cloudflare's Edge network directly from your Kotlin/JVM backend.

## ✨ Features

* **Idiomatic Kotlin**: Clean, type-safe DSL for constructing emails.
* **Coroutines First**: Fully asynchronous, non-blocking network calls.
* **Powered by Ktor**: Uses the highly customizable Ktor HTTP client under the hood.
* **Bring Your Own Engine**: Defaults to Ktor's `CIO` engine, but easily accepts `OkHttp`, `Apache`, or any other Ktor engine.