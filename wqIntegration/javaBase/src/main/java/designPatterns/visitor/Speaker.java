package designPatterns.visitor;

public class Speaker implements IVisitor {

    public static void main(String[] args) {
        Speaker speaker = new Speaker();
        INode cat = new Cat();
        INode dog = new Dog();
        speaker.visit(cat);
        speaker.visit(cat);
        speaker.visit(dog);
    }

    @Override
    public void visit(INode node) {
        node.accept(this);
    }

    @Override
    public void visit(Cat cat){
        System.out.println(cat.getAccessNum());
    }

}
