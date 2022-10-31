package com.example.binaryTree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Stack;

public class HelloController {
    public static int xhead = 550;
    public static int yhead=10;
    public static int x;
    public static int y;

    static Node root;
    static Node prev;
    static boolean sew = false;


    @FXML
    private Button AddButton;

    @FXML
    private TextField TextF;

    @FXML
    private Canvas canvas;

    @FXML
    private Label PramObh;

    @FXML
    private Label SimObh;

    @FXML
    private Label RevObh;

    @FXML
    private Button SewButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField TextF2;

    @FXML
    void onAddClick(ActionEvent event) {
        int value = Integer.parseInt(TextF.getText());
        x = xhead;
        y = yhead;
        root = Insert(root,value);

        displayTree();

         PramObh.setText("Прямой обход: ");
         PramObhod(root);
         String s = PramObh.getText();
         PramObh.setText(s.substring(0,s.length()-2));

         SimObh.setText("Симметричный обход: ");
         SimObhod(root);
         s = SimObh.getText();
         SimObh.setText(s.substring(0,s.length()-2));

         RevObh.setText("Обратный обход: ");
         ObrObhod(root);
         s = RevObh.getText();
         RevObh.setText(s.substring(0,s.length()-2));

    }

    @FXML
    void onSewButtonClick(ActionEvent event){
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        sew = true;
        prev = null;
       Sim_Proshiv(root);
       Obhod_Proshiv_sim(root, true);
       SewNullObhod(root);

        displayTree();
        PramObh.setText("Прямой обход: ");
        PramObhod(root);
        String s = PramObh.getText();
        PramObh.setText(s.substring(0,s.length()-2));

        SimObh.setText("Симметричный обход: ");
        SimObhod(root);
        s = SimObh.getText();
        SimObh.setText(s.substring(0,s.length()-2));

        RevObh.setText("Обратный обход: ");
        ObrObhod(root);
        s = RevObh.getText();
        RevObh.setText(s.substring(0,s.length()-2));
      }

    @FXML
    void onDeleteButtonClick(ActionEvent event){
     if (sew){
      root = DeleteNode(root,Integer.parseInt(TextF2.getText()));
     }
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        prev = null;
        Sim_Proshiv(root);
        Obhod_Proshiv_sim(root, true);
        SewNullObhod(root);
        displayTree();

        PramObh.setText("Прямой обход: ");
        PramObhod(root);
        String s = PramObh.getText();
        PramObh.setText(s.substring(0,s.length()-2));

        SimObh.setText("Симметричный обход: ");
        SimObhod(root);
        s = SimObh.getText();
        SimObh.setText(s.substring(0,s.length()-2));

        RevObh.setText("Обратный обход: ");
        ObrObhod(root);
        s = RevObh.getText();
        RevObh.setText(s.substring(0,s.length()-2));

     sew = false;

    }


    public void Draw(double x, Node root){
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.valueOf("#5B0D31"));
        context.fillOval(x,y,30,30);
        context.setFill(Color.valueOf("#EECAE5"));
        context.fillText(Integer.toString(root.Data), x+11, y+17);
    }

    public void DrawLine(double x, double y, double xk, double yk, boolean f){
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setLineWidth(1);
        context.setStroke(Color.valueOf("#003850"));
       if (f) context.strokeLine(x+15,y+30,xk+30,yk+10);
       else context.strokeLine(x+15,y+30,xk,yk+10);
    }

    public static Node Insert(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.Data) {
              current.Left = Insert(current.Left, value);
        } else if (value > current.Data) {
            current.Right = Insert(current.Right, value);
        } else {
            return current;
        }

        return current;
    }

    public void displayTree()
    {
        Stack globalStack = new Stack();
        globalStack.push(root);
        double emptyLeaf = xhead;
        double xtemp = 0;
        y=0;
        boolean isRowEmpty = false;

        while(!isRowEmpty)
        {

            Stack localStack = new Stack();
            isRowEmpty = true;
            xtemp = emptyLeaf;
            while(!globalStack.isEmpty())
            {
                Node temp = (Node) globalStack.pop();
                if(temp != null)
                {
                    temp.x = xtemp;
                    temp.y = y;
                    Draw(xtemp, temp);

                    localStack.push(temp.Left);
                    localStack.push(temp.Right);
                    if(temp.Left != null || temp.Right != null)
                        isRowEmpty = false;
                }
                else
                {
                    localStack.push(null);
                    localStack.push(null);
                }
                xtemp+= emptyLeaf*2;
            }
            y+=70;
            emptyLeaf /= 2;
            while(!localStack.isEmpty()) globalStack.push( localStack.pop() );
        }

    }


    public void PramObhod(Node root){
        if (root!=null) {
            PramObh.setText(PramObh.getText() + root.Data +" - ");
            if (root.Right!= null) DrawLine(root.x,root.y,root.Right.x,root.Right.y,false);
            if (root.Left!= null) DrawLine(root.x,root.y,root.Left.x,root.Left.y,true);
            PramObhod(root.Left);
            if (root.Left != null) PramObh.setText(PramObh.getText() + "("+root.Data +") - ");
            PramObhod(root.Right);
            if (root.Right != null) PramObh.setText(PramObh.getText() + "("+root.Data +") - ");
        }

    }

    public void SimObhod(Node root){
        if (root!=null){
            if (root.Left != null)  SimObh.setText(SimObh.getText() + "("+ root.Data +") - ");
            SimObhod(root.Left);
            SimObh.setText(SimObh.getText() + root.Data +" - ");
            SimObhod(root.Right);
            if (root.Right != null)  SimObh.setText(SimObh.getText() + "("+ root.Data +") - ");
        }
    }

    public void ObrObhod(Node root){
        if (root!=null){
            if (root.Left != null)  RevObh.setText(RevObh.getText() + "(" + root.Data +") - ");
            ObrObhod(root.Left);
            if (root.Right != null) RevObh.setText(RevObh.getText() + "(" + root.Data +") - ");
            ObrObhod(root.Right);
            RevObh.setText(RevObh.getText() + root.Data +" - ");
        }
    }

    public static void Sim_Proshiv(Node root){
        if (root != null) {
            Sim_Proshiv(root.Left);
            if (prev != null){
                if (prev.Right == null) {
                    prev.rtag = false;
                    prev.Right = root;
                }
                else prev.rtag = true;
            }
            prev = root;
            Sim_Proshiv(root.Right);
        }
    }


    public void Obhod_Proshiv_sim(Node root, boolean f){
        if (root!=null){
            if (f) {
                Obhod_Proshiv_sim(root.Left, true);
            }
            if (!root.rtag && root.Right != null) {
                    GraphicsContext context = canvas.getGraphicsContext2D();
                    context.setStroke(Color.RED);
                    context.setLineWidth(1);
                    context.strokeLine(root.x+28, root.y+20, root.Right.x+15, root.y+20);
                    context.strokeLine(root.Right.x+15, root.y+20, root.Right.x+15, root.Right.y+30);

            }

         //   System.out.print(root.Data + " - " + root.rtag + ", ");
            Obhod_Proshiv_sim(root.Right, root.rtag);
        }
    }

    public void SewNullObhod(Node root){
        if (root!=null) {
            if (!root.rtag) {
                root.rtag = true;
                root.Right = null;
            }
  //          System.out.print(root.Data + " - " + root.rtag + ", ");

            SewNullObhod(root.Left);
            SewNullObhod(root.Right);
        }
    }

    public static Node getMinimumKey(Node curr)
    {
        while (curr.Left != null) {
            curr = curr.Left;
        }
        return curr;
    }

    public static Node DeleteNode(Node root, int key)
    {
        Node parent = null;
        Node curr = root;

        // поиск ключа в BST и установка его родительского указателя
        while (curr != null && curr.Data != key)
        {
            parent = curr;
            if (key < curr.Data) {
                curr = curr.Left;
            }
            else {
                curr = curr.Right;
            }
        }

        // возвращаем, если ключ не найден в дереве
        if (curr == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Нет такого элемента");
            alert.setContentText("Повторите попытку");
            alert.showAndWait();
            return root;
        }

        // Не имеет потомков
        if (curr.Left == null && curr.Right == null)
        {
            if (curr != root)
            {
                if (parent.Left == curr) {
                    parent.Left = null;
                }
                else parent.Right = null;

            }
            else {
                root = null;
            }
        }

        // Имеет двух потомков
        else if (curr.Left != null && curr.Right != null)
        {
            // найти его неупорядоченный узел-преемник
            Node successor = getMinimumKey(curr.Right);

            // сохраняем последующее значение
            int val = successor.Data;

            // рекурсивно удаляем преемника. Обратите внимание, что преемник
            // будет иметь не более одного потомка (правого потомка)
            DeleteNode(root, successor.Data);

            // копируем значение преемника в текущий узел
            curr.Data = val;

        }

        // Имеет одного потомка
        else {
            // выбираем дочерний узел
            Node child = (curr.Left != null)? curr.Left: curr.Right;
            if (curr != root)
            {
                if (curr == parent.Left) {

                    parent.Left = child;

                }
                else {

                    parent.Right = child;
                }
            }
            else {

                root = child;
            }
        }

        return root;
    }

}