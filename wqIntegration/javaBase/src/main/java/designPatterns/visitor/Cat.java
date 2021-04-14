package designPatterns.visitor;

public class Cat implements INode {
    private  int count = 0;

    public int getAccessNum(){
        return count++;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
