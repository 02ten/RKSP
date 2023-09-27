class File {
    private String fileType;
    private int size;

    public File(String fileType, int size) {
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileType() {
        return fileType;
    }

    public int getSize() {
        return size;
    }
}
