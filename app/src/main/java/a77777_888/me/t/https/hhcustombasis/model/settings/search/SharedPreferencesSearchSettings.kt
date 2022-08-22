package a77777_888.me.t.https.hhcustombasis.model.settings.search

import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.EXCLUDE_WORDS
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.EXPERIENCE
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.FIND_IN_COMPANY_NAME
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.FIND_IN_DESCRIPTION
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.FIND_IN_NAME
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.FIND_WORDS
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.PERIOD
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.REGION_ID
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.SCHEDULE_FLEXIBLE
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.SCHEDULE_FLY_IN_FLY_OUT
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.SCHEDULE_FULL_DAY
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.SCHEDULE_REMOTE
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.SCHEDULE_SHIFT
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesSearchSettings
    @Inject constructor(
        @ApplicationContext appContext: Context
    )
    : SearchSettings {

    private val sharedPreferences =
            appContext.getSharedPreferences(SEARCH_SETTINGS, Context.MODE_PRIVATE)

    private val editor = sharedPreferences.edit()

    override var findWords: String? = sharedPreferences.getString(FIND_WORDS, null)
        set(value) {
            if (field != value ) {
                editor.putString(FIND_WORDS, value)
                field = value
            }
        }

    override var excludeWords: String? =
        sharedPreferences.getString(EXCLUDE_WORDS, null)
        set(value) {
             if (field != value) {
                editor.putString(EXCLUDE_WORDS, value)
                field = value
            }
        }

    override var findIn: FindIn = FindIn(
        name = sharedPreferences.getBoolean(FIND_IN_NAME, false),
        description = sharedPreferences.getBoolean(FIND_IN_DESCRIPTION,false),
        companyName = sharedPreferences.getBoolean(FIND_IN_COMPANY_NAME,false)
    )
        set(value) {
            if (field != value) {
                editor.putBoolean(FIND_IN_NAME, value.name)
                editor.putBoolean(FIND_IN_DESCRIPTION, value.description)
                editor.putBoolean(FIND_IN_COMPANY_NAME, value.companyName)
                field = value
            }
        }

    override var regionId: String? = sharedPreferences.getString(REGION_ID, null)
        set(value) {
            if (field != value) {
                editor.putString(REGION_ID, value)
                field = value
            }
        }

    override var experience: String? = sharedPreferences.getString(EXPERIENCE, null)
        set(value) {
            if (field != value) {
                editor.putString(EXPERIENCE, value)
                field = value
            }
        }

    override var schedule: Schedule = Schedule(
        remote = sharedPreferences.getBoolean(SCHEDULE_REMOTE, false),
        fullDay = sharedPreferences.getBoolean(SCHEDULE_FULL_DAY, false),
        shift = sharedPreferences.getBoolean(SCHEDULE_SHIFT, false),
        flexible = sharedPreferences.getBoolean(SCHEDULE_FLEXIBLE, false),
        flyInFlyOut = sharedPreferences.getBoolean(SCHEDULE_FLY_IN_FLY_OUT, false)
    )
        set(value) {
            if (field != value ) {
                editor.putBoolean(SCHEDULE_REMOTE, value.remote)
                editor.putBoolean(SCHEDULE_FULL_DAY, value.fullDay)
                editor.putBoolean(SCHEDULE_SHIFT, value.shift)
                editor.putBoolean(SCHEDULE_FLEXIBLE, value.flexible)
                editor.putBoolean(SCHEDULE_FLY_IN_FLY_OUT, value.flyInFlyOut)
                field = value
            }
        }

    override var period: String? = sharedPreferences.getString(PERIOD, null)
        set(value) {
            if (field != value) {
                editor.putString(PERIOD, value)
            }
            field = value
        }

    fun saveSettings() = editor.commit()

    companion object {
        const val SEARCH_SETTINGS = "search_settings"

    }
}