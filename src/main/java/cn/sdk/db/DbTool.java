package cn.sdk.db;

public class DbTool {
	private final static int USER_TABLES = 100;

	/**
	 * 获取用户所在的分表
	 * 
	 * @param userid
	 * @return
	 */
	public static int getUserTbl(long userid) {
		if (userid <= 0) {
			return 0;
		}

		return (int) userid % USER_TABLES;
	}

	public static void main(String[] args) {
		System.out.println(DbTool.getUserTbl(9000));
	}

}
