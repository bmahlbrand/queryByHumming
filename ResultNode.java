class ResultNode {
	private	String fileName = null;
	private int distance = 0;

	public ResultNode(String fileName, int distance) {
		this.fileName = fileName;
		this.distance = distance;
	}

	public String getFileName() {
		return fileName;
	}

	public int getDistance() {
		return distance;
	}
}