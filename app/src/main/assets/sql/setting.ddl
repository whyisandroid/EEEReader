CREATE TABLE IF NOT EXISTS Setting (
BookCode INTEGER NOT NULL,
FontName TEXT         "TimesRoman",
FontSize INTEGER      DEFAULT  3,
LineSpacing INTEGER   DEFAULT -1,
Foreground INTEGER    DEFAULT -1,
Background INTEGER    DEFAULT -1,
Theme INTEGER         DEFAULT  0,
Brightness REAL       DEFAULT  0,
TransitionType INTEGER DEFAULT 2,
LockRotation INTEGER DEFAULT 0,
DoublePaged  INTEGER DEFAULT 1,
Allow3G		 INTEGER DEFAULT 1,
GlobalPagination INTEGER DEFAULT 0
);
