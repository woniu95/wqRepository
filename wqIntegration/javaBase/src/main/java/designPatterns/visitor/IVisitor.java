package designPatterns.visitor;

public interface IVisitor {

    void visit(INode node);

    void visit(Cat cat);
}
