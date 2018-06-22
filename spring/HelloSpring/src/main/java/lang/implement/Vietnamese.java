package lang.implement;

import lang.Language;

public class Vietnamese implements Language {
    @Override
    public String getGreeting() {
        return "Say chao xin";
    }

    @Override
    public String getBye() {
        return "Say cut con me may di";
    }
}
