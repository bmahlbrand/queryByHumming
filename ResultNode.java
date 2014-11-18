class ResultNode {
	private	String fileName = null;
	private int distance = 0;

	public ResultNode(int distance, String fileName) {
		this.distance = distance;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public int getDistance() {
		return distance;
	}
}