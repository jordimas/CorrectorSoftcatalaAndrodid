/*
 * Copyright (C) 2015 Jordi Mas i Hernàndez <jmas@softcatala.org>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package org.softcatala.corrector;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

import java.util.Date;


/**
 * Preference screen.
 */

public class SpellCheckerSettingsFragment extends PreferenceFragment {

    private static final String TAG = SpellCheckerSettingsFragment.class
            .getSimpleName();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        addPreferencesFromResource(R.xml.spell_checker_settings);

        setHttpConnections();
        setVersion();
        setServer();
        //setDialect();
    }

    private void setDialect() {
        /*Boolean dialect = Configuration.getInstance().getDialect();
        CheckBoxPreference cb = (CheckBoxPreference) findPreference("dialect");
        cb.setChecked(dialect);

        cb.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Boolean dialect = Configuration.getInstance().getDialect();
                Configuration.getInstance().setDialect(!dialect);
                Log.d(TAG, "Pref " + preference.getKey() + " changed to " + newValue.toString());
                return true;
            }
        });*/
    }

    private void setServer() {
        EditTextPreference server = ((EditTextPreference) findPreference("server"));
        server.setSummary(Configuration.getInstance().getServer());

        server.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String newServer = newValue.toString();
                newServer = Configuration.getInstance().setServer(newServer);
                EditTextPreference server = ((EditTextPreference) preference);
                server.setSummary(newServer);
                return true;
            }
        });
    }

    private void setVersion() {
        Date buildDate = BuildConfig.buildTime;
        Preference version = findPreference("version");
        String v = String.format(getResources().getString(R.string.version_text), getVersion(), buildDate);
        version.setSummary(v);
    }

    private String getVersion() {
        try {
            String versionName =
                    getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            return versionName;
        } catch (Exception e) {
            return null;
        }
    }

    private void setHttpConnections() {
        Preference http = findPreference("http");
        Date lastConnection = Configuration.getInstance().getLastConnection();
        String status = String.format(getResources().getString(R.string.connections),
                Configuration.getInstance().getHttpConnections(),
                lastConnection == null ? getResources().getString(R.string.connection_none)
                        : lastConnection.toString());

        http.setSummary(status);
    }
}