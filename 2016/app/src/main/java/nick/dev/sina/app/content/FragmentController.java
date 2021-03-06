/*
 * Copyright (c) 2016 Nick Guo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nick.dev.sina.app.content;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.nick.scalpel.Scalpel;
import com.nick.scalpel.annotation.opt.RetrieveBean;

import java.util.List;

import dev.nick.imageloader.logger.LoggerManager;
import nick.dev.sina.R;
import nick.dev.sina.app.provider.SettingsProvider;

public class FragmentController {

    List<TransactionSafeFragment> mPages;
    FragmentManager mFragmentManager;

    TransactionSafeFragment mCurrent;

    int mDefIndex = 0;

    public FragmentController(FragmentManager manager, List<TransactionSafeFragment> safeFragments) {
        this.mFragmentManager = manager;
        this.mPages = safeFragments;
        init();
    }

    private void init() {
        FragmentManager fragmentManager = mFragmentManager;
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (Fragment fragment : mPages) {
            transaction.add(R.id.container, fragment, fragment.getClass().getSimpleName());
            transaction.hide(fragment);
        }

        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public void setDefaultIndex(int index) {
        mDefIndex = index;
    }

    public TransactionSafeFragment getCurrent() {
        return mCurrent == null ? mPages.get(mDefIndex) : mCurrent;
    }

    public void setCurrent(int index) {
        LoggerManager.getLogger(getClass()).funcEnter();
        FragmentManager fragmentManager = mFragmentManager;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(getCurrent());
        TransactionSafeFragment current = mPages.get(index);
        transaction.show(current);
        transaction.commitAllowingStateLoss();
        mCurrent = current;
    }
}
