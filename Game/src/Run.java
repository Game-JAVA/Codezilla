import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.time.*;

@SuppressWarnings("serial")
public class Run extends javax.swing.JFrame implements Runnable{

    // tamanho da tela
    private int height = 700;
    private int width = 1400;

    // teclas
    /*
     * Funcionamento: quando a tecla é pressionada sua respectiva variável fica
     * com valor True. Quando solta a variável fica com valor False.
     */
    private boolean left, right, up, down, shoot;

    // Construtor
    public Run() {
        // Chama o metodo que realiza todas as configurações iniciais necessárias
        initComponents();

        // Mecanismo de execução paralela
        createBufferStrategy(2);
        Thread t = new Thread(this);
        t.start();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Realiza todas as configurações iniciais necessárias
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Chama os métodos de Scaneamento de teclas (tecla pressionada / solta)
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        // Configura o layout da tela
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // Largura
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, width, Short.MAX_VALUE)
        );
        // Altura
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, height, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Método que verifica se as teclas foram pressionadas
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_W)
        {
            up = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_S) {
            down = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            shoot = true;
        }
    }//GEN-LAST:event_formKeyPressed

    // Método que verifica se as teclas foram soltas
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_W) {
            up = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_S) {
            down = false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            shoot = false;
        }
    }//GEN-LAST:event_formKeyReleased

    // Inicializa a janela
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Run().setVisible(true);
            }
        });
    }
    @Override
    public void run() {
        Graphics g = getBufferStrategy().getDrawGraphics();

        ArrayList<Shoot> shoots = new ArrayList<>();;

        Inimigo n = new Inimigo(10, 10, 1,1, 10,10, "/images/pixil-gif-drawing-5--unscreen.gif");
        n.scale(0.2);

        Clock clock = Clock.systemDefaultZone();
        long milis = clock.millis();
        long milis2 = milis;

        while(true) {
            // Atualiza g
            g = getBufferStrategy().getDrawGraphics();

            //limpa tela
            g.clearRect(0, 0, getWidth(), getHeight());

            // Implemente sua animação aqui.
            n.draw(g);

            if(right)
                n.moveX(1);
            if(left)
                n.moveX(-1);
            if(down)
                n.moveY(1);
            if(up)
                n.moveY(-1);

            milis = clock.millis();

            if((milis - milis2) > 700 && shoot) {
                shoots.add(new Shoot(n.getX(), n.getY(), "/images/tiro.gif", 0.4));
                milis2 = milis;
            }

            for (int i = 0; i < shoots.size(); i++) {
                Shoot s = shoots.get(i);
                s.draw(g);
                if (s.move(height)) {
                    shoots.remove(i);
                    i--;
                }
            }

            // Exibe a tela
            getBufferStrategy().show();

            // Unidade de tempo da animação
            try {
                Thread.sleep(5);
            }catch  (InterruptedException ex) {}
        }
    }
}