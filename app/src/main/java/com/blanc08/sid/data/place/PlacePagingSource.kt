package com.blanc08.sid.data.place

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class PlacePagingSource(
    private val client: SupabaseClient,
    private val query: String
) : PagingSource<Int, Place>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Place> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val places = client.from("place").select().decodeList<Place>()

            LoadResult.Page(
                data = places,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == places.size) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Place>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}