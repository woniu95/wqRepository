package designPatterns.visitor;

public class Dog implements INode {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
