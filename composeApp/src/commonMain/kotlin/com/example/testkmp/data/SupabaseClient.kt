package com.example.testkmp.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://eistlqkbckeieegjvdrk.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVpc3RscWtiY2tlaWVlZ2p2ZHJrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzI5NzAwMjksImV4cCI6MjA4ODU0NjAyOX0.7PZ3uFSwGokgmq8XCszN8LR8xP1X-HfSuW1Hbega-fU"
) {
    install(Postgrest)
    install(Auth) {
        scheme = "app"
        host = "supabase.co"
    }
}