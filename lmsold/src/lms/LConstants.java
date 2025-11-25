package lms;

public class LConstants {
	public final static int LFRAME_X = 400;
	public final static int LFRAME_Y = 100;
	public final static int LFRAME_W = 800;
	public final static int LFRAME_H = 400;
	
	public enum EIndexName {
		eCampus("대학"),
		eCollege("학부"),
		eDepartment("학과");
		
		private String text;
		private EIndexName(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EIndex {
		eId("아이디"),
		eName("이름"),
		eFileName("파일명");
		
		private String text;
		private EIndex(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	public enum EGangjwa {
		eId("아이디"),
		eName("이름"),
		eLecturer("과목"),
		eCredit("학점"),
		eTime("시간");
		
		private String text;
		private EGangjwa(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
}
