/*
 * Copyright (c) 2017 Martin Pfeffer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pepperonas.showcase;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.pepperonas.materialdialog.MaterialDialog;
import com.pepperonas.materialdialog.MaterialDialog.Builder;
import com.pepperonas.materialdialog.MaterialDialog.ButtonCallback;
import com.pepperonas.materialdialog.MaterialDialog.FileChooserListener;
import com.pepperonas.materialdialog.adapter.ShareAdapter;
import com.pepperonas.materialdialog.model.Changelog;
import com.pepperonas.materialdialog.model.LicenseInfo;
import com.pepperonas.materialdialog.model.ReleaseInfo;
import com.pepperonas.materialdialog.utils.Const;
import com.pepperonas.materialdialog.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String[] ITEMS = new String[]{"Car", "Plane", "Bike", "Skateboard", "Rocket",
        "Paper plane", "Boat", "Train",
        "Hovercraft", "Space shuttle", "Jet", "Truck", "Elephant"};

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("About");
        menu.add(mSharedPreferences.getBoolean("SHOW_TOASTS", true) ? Const.HIDE_TOASTS
            : Const.SHOW_TOASTS);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("About")) {
            new MaterialDialog.Builder(this)
                .title("MaterialDialog library")
                .customView(R.layout.dialog_lib_info)
                .positiveText("OK")
                .positiveColor(R.color.grey_700)
                .icon(R.drawable.ic_launcher)
                .showListener(new MaterialDialog.ShowListener() {
                    @Override
                    public void onShow(AlertDialog d) {
                        super.onShow(d);
                        TextView tvLibInfo = (TextView) d.findViewById(R.id.tv_lib_info);
                        tvLibInfo.setText(Html.fromHtml(getString(R.string.web_presentation_info)));
                        tvLibInfo.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                })
                .show();
        } else if (item.getTitle().equals(Const.HIDE_TOASTS)) {
            mSharedPreferences.edit().putBoolean("SHOW_TOASTS", false).apply();
            item.setTitle(Const.SHOW_TOASTS);
        } else if (item.getTitle().equals(Const.SHOW_TOASTS)) {
            mSharedPreferences.edit().putBoolean("SHOW_TOASTS", true).apply();
            item.setTitle(Const.HIDE_TOASTS);
        }
        return super.onOptionsItemSelected(item);
    }


    private void showMaterialDialog() {
        new MaterialDialog.Builder(this)
            .title("MaterialDialog")
            .message("A simple dialog.")
            .positiveText("OK")
            .neutralText("NOT NOW")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .neutralColor(R.color.yellow_700)
            .negativeColor(R.color.pink_700)
            .showListener(new MaterialDialog.ShowListener() {
                @Override
                public void onShow(AlertDialog d) {
                    super.onShow(d);
                }
            })
            .dismissListener(new MaterialDialog.DismissListener() {
                @Override
                public void onDismiss() {
                    super.onDismiss();
                }
            })
            .buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    showToast("Ok");
                }


                @Override
                public void onNeutral(MaterialDialog dialog) {
                    super.onNeutral(dialog);
                    showToast("Not now");
                }


                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    showToast("Cancel");
                }
            })
            .show();
    }


    private void showMaterialDialogIgnoredButtonCallback() {
        new MaterialDialog.Builder(this)
            .title("MaterialDialog")
            .message("Ignored Buttons.")
            .positiveText("OK")
            .neutralText("NOT NOW")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .neutralColor(R.color.yellow_700)
            .negativeColor(R.color.pink_700)
            .showListener(new MaterialDialog.ShowListener() {
                @Override
                public void onShow(AlertDialog d) {
                    super.onShow(d);
                    d.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(
                        new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("Not closed...");
                            }
                        });
                }
            })
            .buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    showToast("Ok");
                }


                @Override
                public void onNeutral(MaterialDialog dialog) {
                    super.onNeutral(dialog);
                    showToast("Not now");
                }


                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    showToast("Cancel");
                }
            })
            .show();
    }


    private void showMaterialDialogDelayedButton() {
        new MaterialDialog.Builder(this)
            .title("Important")
            .message("Please read our terms of use.")
            .positiveText("DONE")
            .positiveColor(R.color.green_700)
            .positiveDelayed(3000, 1000, "OK")
            .cancelable(false)
            .canceledOnTouchOutside(false)
            .buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    showToast("Ok");
                }
            })
            .dismissListener(new MaterialDialog.DismissListener() {
                @Override
                public void onDismiss() {
                    super.onDismiss();
                }
            })
            .show();
    }


    private void showMaterialDialogCustomView() {
        new MaterialDialog.Builder(this)
            .title("MaterialDialog")
            .message("Icon above and a custom view below...")
            .positiveText("AGREE")
            .negativeText("DISAGREE")
            .customView(R.layout.dialog_custom_view)
            .positiveColor(R.color.green_700)
            .neutralColor(R.color.yellow_700)
            .negativeColor(R.color.pink_700)
            .icon(R.drawable.spartan_helmet_256)
            .buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    showToast("Agreed");
                }


                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    showToast("Disagreed");
                }
            })
            .show();
    }


    private void showFileChooserDialog() {
        new Builder(this)
            .fileChooserDialog(this, true, null, new FileChooserListener() {
                @Override
                public void onFileSelected(MaterialDialog dialog, View view, int position, long id,
                    File file) {
                    super.onFileSelected(dialog, view, position, id, file);
                    showToast("onFileSelected (" + file.getPath() + ")");
                    dialog.dismiss();
                }

                @Override
                public void onFileSet(MaterialDialog dialog, File file) {
                    super.onFileSet(dialog, file);
                    showToast("onFileSet (" + file.getPath() + ")");
                }
            })
            .buttonCallback(new ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                }
            })
            .positiveText("OK")
            .show();
    }


    private void showDirectoryChooserDialog() {
        new Builder(this)
            .negativeText("CANCEL")
            .fileChooserDialog(this, false, null, new FileChooserListener() {
                @Override
                public void onFileSelected(MaterialDialog dialog, View view, int position, long id,
                    File file) {
                    super.onFileSelected(dialog, view, position, id, file);
                    showToast("onFileSelected (" + file.getPath() + ")");
                    dialog.dismiss();
                }

                @Override
                public void onFileSet(MaterialDialog dialog, File file) {
                    super.onFileSet(dialog, file);
                    showToast("onFileSet (" + file.getPath() + ")");
                }
            })
            .buttonCallback(new ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                }
            })
            .positiveText("OK")
            .show();
    }


    private void showMaterialDialogListNoTitle() {
        new MaterialDialog.Builder(this)
            .title(null)
            .listItems(true, "HTC", "Samsung", "LG", "Huawei")
            .itemClickListener(new MaterialDialog.ItemClickListener() {
                @Override
                public void onClick(View v, int position, long id) {
                    super.onClick(v, position, id);
                    showToast("onClick (" + ((TextView) ((LinearLayout) v).getChildAt(0)).getText()
                        + ")");
                    Log.d(TAG, "onClick (" + ((TextView) ((LinearLayout) v).getChildAt(0)).getText()
                        + ")");
                }
            })
            .show();
    }


    private void showMaterialDialogList() {
        new MaterialDialog.Builder(this)
            .title("MaterialDialog")
            .negativeText("CANCEL")
            .negativeColor(R.color.pink_500)
            .listItems(false, ITEMS)
            .itemSelectedListener(new MaterialDialog.ItemSelectedListener() {
                @Override
                public void onSelected(View view, int position, long id) {
                    super.onSelected(view, position, id);
                }
            })
            .itemClickListener(new MaterialDialog.ItemClickListener() {
                @Override
                public void onClick(View v, int position, long id) {
                    super.onClick(v, position, id);
                    showToast("onClick (" + ITEMS[position] + ")");
                }
            })
            .buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    showToast("Cancel");
                }
            })
            .show();
    }


    private void showMaterialDialogListSingleChoice() {
        new MaterialDialog.Builder(this)
            .title("List dialog")
            .message(null)
            .positiveText("OK")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .negativeColor(R.color.pink_700)
            .listItemsSingleSelection(false, ITEMS)
            .selection(2)
            .itemClickListener(new MaterialDialog.ItemClickListener() {
                @Override
                public void onClick(View v, int position, long id) {
                    super.onClick(v, position, id);
                    showToast("onClick (" + ITEMS[position] + ")");
                }
            })
            .itemLongClickListener(new MaterialDialog.ItemLongClickListener() {
                @Override
                public void onLongClick(View view, int position, long id) {
                    super.onLongClick(view, position, id);
                    showToast("onLongClick (" + ITEMS[position] + ")");
                }
            })
            .showListener(new MaterialDialog.ShowListener() {
                @Override
                public void onShow(AlertDialog dialog) {
                    super.onShow(dialog);
                }
            })
            .buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    showToast("OK");
                }


                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    showToast("Cancel");
                }
            })
            .show();
    }


    private void showMaterialDialogListMultiChoice() {
        new MaterialDialog.Builder(this)
            .title("MaterialDialog")
            .message(null)
            .positiveText("OK")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .negativeColor(R.color.pink_700)
            .listItemsMultiChoice(ITEMS)
            .selection(0, 2)
            .itemClickListener(new MaterialDialog.ItemClickListener() {
                @Override
                public void onClick(View v, int position, long id) {
                    super.onClick(v, position, id);
                    showToast("onClick (" + ITEMS[position] + ")");
                }
            })
            .buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    showToast("Ok");
                }


                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    showToast("Canceled");
                }
            }).build().show();

    }


    private void showMaterialDialogBaseAdapter() {
        ShareAdapter sa = new ShareAdapter(this);

        new MaterialDialog.Builder(this)
            .title("Base Adapter")
            .negativeText("Cancel")
            .icon(R.drawable.ic_launcher)
            .adapter(true, sa, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showToast(
                        "onClick (" + ((TextView) ((LinearLayout) view).getChildAt(1)).getText()
                            + ")");
                }
            })
            .show();
    }


    private void showMaterialDialogShareApp() {
        new MaterialDialog.Builder(this)
            .title("Share app")
            .negativeText("Cancel")
            .icon(R.drawable.ic_launcher)
            .shareAppDialog(true, "Hey, check out this app:\n")
            .show();
    }


    private void showMaterialDialogLicenseInfo() {
        List<LicenseInfo> licenseInfos = getLicenseInfos();
        Typeface tf = Utils.resolveTypeface(this, "sys_san_fran.ttf");

        new MaterialDialog.Builder(this)
            .title(R.string.dialog_license_title)
            .licenseDialog(licenseInfos)
            .positiveText(R.string.ok)
            .font(tf)
            .show();
    }


    private void showMaterialDialogChangelog() {
        List<Changelog> changelogs = getChangelogs();

        new MaterialDialog.Builder(this)
            .title("Changelog")
            .changelogDialog(changelogs, getString(R.string.bullet_release_info))
            .positiveText(R.string.ok)
            .show();
    }


    private void showDialogScaled() {
        new MaterialDialog.Builder(this)
            .title("Scaled dialog")
            .message(
                "Lorem ipsum dolor sit amet, eu vitae petentium eam. Prima nominavi eloquentiam pri ne, vix eu case "
                    +
                    "dicant persequeris. Tale natum molestie vel no, civibus appareat nominati no eum. Mediocrem sententiae"
                    +
                    " usu in, vix dicunt conclusionemque ne, ut mel error insolens. Pro at fugit legendos, per ne fastidii "
                    +
                    "maiestatis. Eum tale idque ex. Veri autem aliquip nam et, numquam albucius recusabo per cu.\n"
                    +
                    "\n" +
                    "Vis id aperiam habemus imperdiet, eu tollit sensibus accommodare vix. Ea decore epicurei eam. Te vitae"
                    +
                    " saperet appareat eam, modus ullum expetendis mei cu. Omnes viderer vis eu, eam ei ornatus "
                    +
                    "intellegebat. Vim posse liberavisse an, oratio accumsan euripidis ex duo, eu fabellas platonem "
                    +
                    "mandamus eum.\n" +
                    "\n" +
                    "Ea usu tota tempor comprehensam, ea essent nominavi eam. An apeirian comprehensam vix, oblique dolorum"
                    +
                    " sed eu, at vis nullam mandamus corrumpit. Ei est nominati urbanitas, appetere complectitur quo eu, "
                    +
                    "doming erroribus mei id. In nihil tincidunt duo, has illud graeco partiendo te. Mea eu hinc "
                    +
                    "contentiones, sed no natum salutatus. Per vero essent voluptatibus ut, eu duo laudem singulis, ex erat"
                    +
                    " zril dolorum mea. Te pri justo tation graecis.\n" +
                    "\n" +
                    "Tollit ornatus persequeris ei mea, vix et rebum invenire. No ancillae conclusionemque vis. Nec dico "
                    +
                    "splendide dissentiunt in, aeque iusto ne has. Illud integre vix ne, mei cu agam maiestatis, ut nec "
                    +
                    "meliore percipitur.\n" +
                    "\n" +
                    "Clita democritum persequeris et duo, ut has nibh iudicabit, id eam volutpat instructior. Vel ea "
                    +
                    "appareat deseruisse, eu est graeco feugiat, nam eu iusto oratio animal. Ad eum saepe dictas "
                    +
                    "efficiendi, postea pertinax mei eu, sit ad quod quodsi meliore. Cu cum lobortis platonem erroribus, eu"
                    +
                    " suas sanctus constituto vis, regione accommodare ei usu. Ut consul melius senserit cum, sit regione "
                    +
                    "minimum scribentur cu, in hinc delenit habemus eum. Pro in semper torquatos referrentur. Ut mel mazim "
                    +
                    "fierent, vix no nibh ornatus salutatus.")
            .scale(60, 100)
            .positiveText(R.string.ok)
            .show();
    }


    private void showCustomTypefaceDialog() {
        Typeface tf = Utils.resolveTypeface(this, "hacked.ttf");
        new MaterialDialog.Builder(this)
            .title("Custom font")
            .message("This font looks nice.")
            .positiveText("OK")
            .negativeText("CANCEL")
            .font(tf)
            .show();
    }


    private void showDialogThemed() {
        new MaterialDialog.Builder(this, R.style.CustomStyle)
            .title("Themed dialog")
            .message("It's customized...")
            .positiveText(R.string.ok)
            .show();
    }


    private void showDialogFullscreen() {
        new MaterialDialog.Builder(this)
            .title("Fullscreen dialog")
            .customView(R.layout.dialog_fullscreen)
            .positiveText(R.string.ok)
            .fullscreen(true)
            .show();
    }


    private void showDialogDimmed() {
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        new MaterialDialog.Builder(this)
            .title("MaterialDialog")
            .message("Dimmed MaterialDialog.")
            .positiveText("OK")
            .positiveColor(R.color.purple_700)
            .dim(seekBar.getProgress())
            .show();
    }


    public void onDialog(View view) {
        showMaterialDialog();
    }

    public void onDialogIgnoredButtonCallback(View view) {
        showMaterialDialogIgnoredButtonCallback();
    }


    public void onDialogDelayedButton(View view) {
        showMaterialDialogDelayedButton();
    }


    public void onDialogCustomView(View view) {
        showMaterialDialogCustomView();
    }


    public void onDialogFileChooser(View view) {
        if (!checkPermission()) {
            return;
        }
        showFileChooserDialog();
    }

    public void onDialogDirectoryChooser(View view) {
        if (!checkPermission()) {
            return;
        }
        showDirectoryChooserDialog();
    }


    public void onDialogListNoTitle(View view) {
        showMaterialDialogListNoTitle();
    }


    public void onDialogList(View view) {
        showMaterialDialogList();
    }


    public void onDialogListSingleChoice(View view) {
        showMaterialDialogListSingleChoice();
    }


    public void onDialogListMultiChoice(View view) {
        showMaterialDialogListMultiChoice();
    }


    public void onDialogBaseAdapter(View view) {
        showMaterialDialogBaseAdapter();
    }


    public void onDialogShare(View view) {
        showMaterialDialogShareApp();
    }


    public void onDialogLicense(View view) {
        showMaterialDialogLicenseInfo();
    }


    public void onDialogChangelog(View view) {
        showMaterialDialogChangelog();
    }


    @NonNull
    private List<LicenseInfo> getLicenseInfos() {
        List<LicenseInfo> licenseInfos = new ArrayList<>();

        licenseInfos.add(new LicenseInfo(
            "Standard Library",
            "Copyright (c) 2016 John Doe",
            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        licenseInfos.add(new LicenseInfo(
            "Default Library",
            "Copyright (c) 2015 Jane Doe",
            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        licenseInfos.add(new LicenseInfo(
            "Common Library",
            "Copyright (c) 2014 James Doe",
            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        return licenseInfos;
    }


    private List<Changelog> getChangelogs() {
        List<Changelog> changelogs = new ArrayList<>();
        changelogs.add(new Changelog("0.1.3", "2016-02-13",
            new ReleaseInfo("Revised list-dialogs", "Added default view " +
                "spacing")));
        changelogs.add(new Changelog("0.1.2", "2016-02-07",
            new ReleaseInfo("Added styles", "Layout alignment", "Bugfixes")));
        changelogs
            .add(new Changelog("0.1.1", "2016-02-06", new ReleaseInfo("Removed unused resources")));
        changelogs.add(new Changelog("0.1.0", "2016-01-26", new ReleaseInfo("Fixed scroll")));
        changelogs.add(new Changelog("0.0.9", "2016-01-24",
            new ReleaseInfo("Added themed dialog", "Bugfixes")));
        changelogs.add(new Changelog("0.0.8", "2016-01-23", new ReleaseInfo("Bugfixes")));
        changelogs.add(new Changelog("0.0.7", "2016-01-14",
            new ReleaseInfo("Added licenses dialog", "Added changelog dialog")));
        changelogs.add(new Changelog("0.0.6", "2016-01-10",
            new ReleaseInfo("Added custom-view dialog", "Added dimmed dialog",
                "Bugfixes")));
        changelogs.add(new Changelog("0.0.5", "2016-01-08",
            new ReleaseInfo("Added dialog with delayed button")));
        changelogs.add(new Changelog("0.0.4", "2016-01-05", new ReleaseInfo("Bugfixes")));
        changelogs.add(new Changelog("0.0.3", "2016-01-04", new ReleaseInfo("Layout alignment")));
        changelogs.add(new Changelog("0.0.2", "2016-01-03", new ReleaseInfo("Fixed null-pointer")));
        changelogs.add(new Changelog("0.0.1", "2016-01-02", new ReleaseInfo("Initial release")));
        return changelogs;
    }


    public void onDialogScaled(View view) {
        showDialogScaled();
    }


    public void onDialogCustomTypeface(View view) {
        showCustomTypefaceDialog();
    }


    public void onDialogThemed(View view) {
        showDialogThemed();
    }


    public void onDialogFullscreen(View view) {
        showDialogFullscreen();
    }

    public void onDialogDimmed(View view) {
        showDialogDimmed();
    }


    private void showToast(String text) {
        if (!mSharedPreferences.getBoolean("SHOW_TOASTS", true)) {
            return;
        }
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    private boolean checkPermission() {
        int permission = ActivityCompat
            .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
        int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (!(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this,
                        "The app was not allowed to write to your storage. Hence, it cannot function"
                            +
                            " properly. Please consider granting it this permission",
                        Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
