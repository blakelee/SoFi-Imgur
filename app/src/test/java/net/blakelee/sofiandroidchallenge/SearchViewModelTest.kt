package net.blakelee.sofiandroidchallenge

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import net.blakelee.model.business.ImageModel
import net.blakelee.sofiandroidchallenge.viewmodels.SearchResults
import net.blakelee.sofiandroidchallenge.viewmodels.SearchViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit
import org.mockito.Mockito.`when` as whenever

class SearchViewModelTest {

    @Mock private lateinit var model: ImageModel

    private val testScheduler = TestScheduler()

    private val vm by lazy { SearchViewModel(model) }

    @Before
    fun setup() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }

        MockitoAnnotations.initMocks(this)

        whenever(model.loadImages(Matchers.anyInt(), Matchers.anyString()))
            .thenReturn(Single.just(listOf()))
    }

    @Test
    fun `Test we get a result after a delay`() {
        val testSubscription = vm.results().test()

        vm.loadImages("Hello")

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        vm.loadImages("Hello world")

        testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)

        assert(testSubscription.values().contains(SearchResults.Replace(listOf())))
    }

    @Test
    fun `Test no empty string results`() {
        val testSubscription = vm.results().test()

        vm.loadImages("")

        testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)

        assert(testSubscription.valueCount() == 0)
    }

    @Test
    fun `Test load new page`() {
        val testSubscription = vm.results().test()

        vm.loadImages("Hello world")

        testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)

        vm.loadNextPage()

        assert(testSubscription.values().contains(SearchResults.Append(listOf())))
    }

    @Test
    fun `Test load new page but no text`() {
        val testSubscription = vm.results().test()

        vm.loadImages("")

        testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)

        vm.loadNextPage()

        assert(!testSubscription.values().contains(SearchResults.Append(listOf())))
    }
}