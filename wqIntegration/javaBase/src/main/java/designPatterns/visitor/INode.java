package designPatterns.visitor;

public interface INode {

    void accept(IVisitor visitor);

}
