package com.ereader.reader.activity;

/*public class GridItemMenuDialog extends Dialog implements View.OnClickListener {

	MainActivity mActivity = null;
	StoreBook mStoreBook;

	Button mOpen, mDelete, mCancel;

	AlertDialog mDeleteConformDialog;

	public GridItemMenuDialog(MainActivity activity, StoreBook storeBook) {
		super(activity, R.style.GridItemMenuTheme);
		mActivity = activity;
		mStoreBook = storeBook;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		getWindow().getDecorView().setPadding(0, 0, 0, 0);

		setContentView(R.layout.layout_grid_item_menu);
		mOpen = (Button) findViewById(R.id.open);
		mOpen.setOnClickListener(this);
		mDelete = (Button) findViewById(R.id.delete);
		mDelete.setOnClickListener(this);
		mCancel = (Button) findViewById(R.id.cancel);
		mCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();
		if (v == mOpen) {
			mActivity.openBook(mStoreBook);
		} else if (v == mDelete) {
			conformDelete();
		} else if (v == mCancel) {
		}
	}

	private void conformDelete() {
		if (mDeleteConformDialog != null && mDeleteConformDialog.isShowing()) {
			try {
				mDeleteConformDialog.dismiss();
			} catch(Throwable tr) {}
		}
		final boolean[] checkedItems = new boolean[1];
		mDeleteConformDialog = new AlertDialog.Builder(getContext(), R.style.BookDeleteConformDialogTheme)
		.setTitle(getContext().getResources().getString(R.string.book_delete_conform_title, mStoreBook.name))
		.setPositiveButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (checkedItems[0]) {
					File f = new File(mStoreBook.file);
					f.delete();
				}
				FileUtils.delete(new File(LocalCache.instance(getContext()).getCachePath(FileUtils.getBookCacheDir(mStoreBook))));
				BookDBHelper.get(getContext()).deleteBook(mStoreBook);
				mActivity.reloadData();
			}
		})
		.setNegativeButton(R.string.cancel, null)
		.setMultiChoiceItems(new String[]{getContext().getResources().getString(R.string.book_delete_source_file)}, checkedItems, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				checkedItems[0] = isChecked;
			}
		})
		.show();
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
		// dismiss delete conform dialog
		if (mDeleteConformDialog != null && mDeleteConformDialog.isShowing()) {
			try {
				mDeleteConformDialog.dismiss();
			} catch(Throwable tr) {}
		}
	}
}*/
