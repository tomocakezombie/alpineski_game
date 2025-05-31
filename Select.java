public class Select {
    private int maxSelection;
    private int currentSelection;
    private String[] options;

    public Select(String[] options) {
        if (options == null || options.length == 0) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }
        this.options = options;
        this.maxSelection = options.length - 1;
        this.currentSelection = 0;
    }

    public void next() {
        currentSelection = (currentSelection + 1) % options.length;
    }
    
    public void previous() {
        currentSelection = (currentSelection - 1 + options.length) % options.length;
    }
    
    public String getCurrentSelection() {
        return options[currentSelection];
    }

    public int getCurrentIndex() {
        return currentSelection;
    }

    public void reset() {
        currentSelection = 0;
    }

    public int getMaxSelection() {
        return maxSelection;
    }

    public String[] getOptions() {
        return options;
    }
}
