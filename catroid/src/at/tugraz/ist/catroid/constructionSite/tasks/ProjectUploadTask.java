/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.tugraz.ist.catroid.constructionSite.tasks;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
import at.tugraz.ist.catroid.Consts;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.utils.UtilZip;
import at.tugraz.ist.catroid.web.ConnectionWrapper;
import at.tugraz.ist.catroid.web.WebconnectionException;

public class ProjectUploadTask extends AsyncTask<Void, Void, Boolean> {

	public static boolean mUseTestUrl = false;

	private Context mContext;
	private String mProjectPath;
	private String mZipFile;
	private ProgressDialog mProgressdialog;
	private String mProjectName;
	private String resultString;

	// mock object testing
	protected ConnectionWrapper createConnection() {
		return new ConnectionWrapper();
	}

	public ProjectUploadTask(Context context, String projectName, String projectPath, String zipFile) {
		mContext = context;
		mProjectPath = projectPath;
		mZipFile = zipFile;
		mProjectName = projectName;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(mContext == null)
			return;
		String title = mContext.getString(R.string.please_wait);
		String message = mContext.getString(R.string.loading);
		mProgressdialog = ProgressDialog.show(mContext, title,
				message);
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		try {
			File dirPath = new File(mProjectPath);
			String[] pathes = dirPath.list(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					if(filename.endsWith(Consts.PROJECT_EXTENTION) || filename.equalsIgnoreCase("images")
							|| filename.equalsIgnoreCase("sounds"))
						return true;
					return false;
				}
			});
			if(pathes == null)
				return false;

			for(int i=0;i<pathes.length;++i) {
				pathes[i] = dirPath +"/"+ pathes[i];
			}

			File file = new File(mZipFile);
			if(!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			if (!UtilZip.writeToZipFile(pathes, mZipFile)) {
				file.delete();
				return false;
			}
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(Consts.PROJECT_NAME_TAG, mProjectName);

			String serverUrl;
			if(true)//mUseTestUrl)
				serverUrl = Consts.TEST_FILE_UPLOAD_URL;
			else
				serverUrl = Consts.FILE_UPLOAD_URL;
			resultString = createConnection().doHttpPostFileUpload(serverUrl, hm, Consts.FILE_UPLOAD_TAG, mZipFile);
			file.delete();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WebconnectionException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(mProgressdialog != null && mProgressdialog.isShowing())
			mProgressdialog.dismiss();

		if(!result) {
			showDialog(R.string.error_project_upload);
			return;
		}

		showDialog(R.string.success_project_upload);

	}

	private void showDialog(int messageId) {
		if(mContext == null)
			return;
		new Builder(mContext)
		.setMessage(messageId)
		.setPositiveButton("OK", null)
		.show();
	}

	public String getResultString() {
		return resultString;
	}

}
