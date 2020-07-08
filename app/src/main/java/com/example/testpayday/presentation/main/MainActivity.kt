package com.example.testpayday.presentation.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.testpayday.R
import com.example.testpayday.core.base.presentation.BaseActivity
import com.example.testpayday.core.base.presentation.BaseFragment
import com.example.testpayday.core.library.viewpager.BaseFragmentStateAdapter
import com.example.testpayday.presentation.account.AccountFragment
import com.example.testpayday.presentation.dashboard.DashboardFragment
import com.example.testpayday.presentation.transactions.TransactionsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun diModule() = Kodein.Module(MainActivity::class.java.simpleName) {

    }

    private val router: MainRouter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        initViews()
    }

    override fun onStop() {
        router.detach()
        super.onStop()
    }

    private fun initViews() {
        setupViewPager()
        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> viewPager.currentItem = DASHBOARD_FRAGMENT_INDEX
                R.id.transactions -> viewPager.currentItem = TRANSACTIONS_FRAGMENT_INDEX
                R.id.account -> viewPager.currentItem = ACCOUNT_FRAGMENT_INDEX
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            router.navigateToLogin(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager() {
        val fragments = listOf<BaseFragment>(
            DashboardFragment.newInstance(),
            TransactionsFragment.newInstance(),
            AccountFragment.newInstance()
        )
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = BaseFragmentStateAdapter(
            rootActivity = this,
            fragments = fragments
        )
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    DASHBOARD_FRAGMENT_INDEX -> {
                        bottomBar.menu.getItem(DASHBOARD_FRAGMENT_INDEX).isChecked = true
                        supportActionBar?.setTitle(R.string.dashboard)
                    }
                    TRANSACTIONS_FRAGMENT_INDEX -> {
                        bottomBar.menu.getItem(TRANSACTIONS_FRAGMENT_INDEX).isChecked = true
                        supportActionBar?.setTitle(R.string.transactions)
                    }
                    ACCOUNT_FRAGMENT_INDEX -> {
                        bottomBar.menu.getItem(ACCOUNT_FRAGMENT_INDEX).isChecked = true
                        supportActionBar?.setTitle(R.string.account)
                    }
                }
            }
        })
    }

    companion object {
        private const val DASHBOARD_FRAGMENT_INDEX = 0
        private const val TRANSACTIONS_FRAGMENT_INDEX = 1
        private const val ACCOUNT_FRAGMENT_INDEX = 2
    }
}
