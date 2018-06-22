package lang.implement;

import lang.Language;

public class English implements Language {
    @Override
    public String getGreeting() {
        return "Say hello";
    }

    @Override
    public String getBye() {
        return "Say goodbye";
    }
}
