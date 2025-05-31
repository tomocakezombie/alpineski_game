import java.util.ArrayList;
import java.util.List;

public class UserSelectionManager {
    private maxSelection;
    private List<String> options; // 選択肢のリスト
    private int selectedOption; // 現在の選択内容
    private boolean isConfirmed; // 選択が確定されたかどうか
    
    public UserSelectionManager(int maxSelection) {
        this.maxSelection = maxSelection;
        this.options = new ArrayList<>();
        this.selectedOption = null;
        this.isConfirmed = false;
    }

    public void addOption(String option){
        if (options.size() < maxSelection) {
            options.add(option);
        } else {
            throw new IllegalStateException("Maximum selection limit reached: " + maxSelection);
        }
    }

    public void removeOption(String option) {
        options.remove(option);
    }

    public List<String> getNowSelectOption() {
        return options[selectedOption];
    }

    


}