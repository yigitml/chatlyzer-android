package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.domain.model.Analysis
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton repository to hold transient Ghost Mode analysis results.
 * This data is not persisted and will be cleared when explicitly requested
 * or when the app process is killed.
 */
@Singleton
class GhostResultRepository @Inject constructor() {
    
    private var ghostResults: List<Analysis>? = null
    
    fun setResults(results: List<Analysis>) {
        ghostResults = results
    }
    
    fun getResults(): List<Analysis>? {
        return ghostResults
    }
    
    fun clearResults() {
        ghostResults = null
    }
}
