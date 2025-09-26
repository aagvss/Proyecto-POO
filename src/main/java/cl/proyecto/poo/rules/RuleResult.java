package cl.proyecto.poo.rules;

public class RuleResult {
    private final boolean pass;
    private final String ruleId;
    private final String message;

    public RuleResult(boolean pass, String ruleId, String message) {
        this.pass = pass;
        this.ruleId = ruleId;
        this.message = message;
    }

    public boolean isPass() { return pass; }
    public String getRuleId() { return ruleId; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "RuleResult{" + "pass=" + pass + ", ruleId='" + ruleId + '\'' + ", message='" + message + '\'' + '}';
    }
}
