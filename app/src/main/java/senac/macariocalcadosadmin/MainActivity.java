package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import senac.macariocalcadosadmin.adapters.ViewPagerAdapter;
import senac.macariocalcadosadmin.firebase.Conexao;
import senac.macariocalcadosadmin.fragments.BuscarFragment;
import senac.macariocalcadosadmin.fragments.InserirFragment;
import senac.macariocalcadosadmin.fragments.VisualizarFragment;
import senac.macariocalcadosadmin.models.Sapato;
import senac.macariocalcadosadmin.models.SelecaoSapato;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private VisualizarFragment visualizarFragment;
    private BuscarFragment buscarFragment;
    private InserirFragment inserirFragment;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;

    public static  List<SelecaoSapato> listaSapatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        setView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
    }

    private final String LIST_SHOES_STATE = "lista de sapatos";

    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_SHOES_STATE,
                (ArrayList<? extends Parcelable>) listaSapatos);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            listaSapatos = savedInstanceState.getParcelableArrayList(LIST_SHOES_STATE);
        }
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar_main);
        bottomNavigationView = findViewById(R.id.bn_menu);
        viewPager = findViewById(R.id.vp_fragments);
    }

    private void setView() {
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_main);
        getSupportActionBar().setTitle("");
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        viewPager.addOnPageChangeListener(MainActivity.this);
        setViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        visualizarFragment = new VisualizarFragment();
        buscarFragment = new BuscarFragment();
        inserirFragment = new InserirFragment();
        adapter.addFragment(visualizarFragment);
        adapter.addFragment(buscarFragment);
        adapter.addFragment(inserirFragment);
        viewPager.setAdapter(adapter);
    }

    /*
     *  Métodos associados ao toolbar:
     *      onCreateOptionsMenu(Menu)
     *      onOptionsItemSelected(MenuItem)
     */

    /*
     *  onCreateOptionsMenu(Menu): Inicializa o conteúdo das opções padrões do menu da atividade,
     *      isto é, o conteúdo de menu xml a um objeto tipo Menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }


    /*
     *  onOptionsItemSelected(MenuItem): Controla o comportamento das opções disponíveis no menu do
     *      toolbar quando acionadas.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sair:
                logout();
                break;
            case R.id.menu_configuracoes:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Conexao.logOut();
        finish();
    }

    /*
     *  Métodos associados ao Bottom Navigation:
     *      onNavigationItemSelected(MenuItem)
     */

    /*
     *  onNavigationItemSelected(MenuItem): Controla o comportamento das opções disponíveis no menu
     *      do bottom navigation quando acionadas.
     */
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_visualizar:{
                viewPager.setCurrentItem(0);
                break;
            }
            case R.id.menu_buscar:{
                viewPager.setCurrentItem(1);
                break;
            }
            case R.id.menu_inserir:{
                //inserirFragment.atualizaListaSapato(listaSapatos);
                viewPager.setCurrentItem(2);
                break;
            }
        }
        return false;
    }

    /*
     *  onPageScrolled(int,float,int): Esse método será invocado quando a página atual for rolada,
     *      como parte de uma rolagem suave iniciada programaticamente ou de uma rolagem de toque
     *      iniciada pelo usuário.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /*
     *  onPageSelected(int): Este método será invocado quando uma nova página for selecionada. A
     *      animação não está necessariamente completa.
     */
    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else
        {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);
    }

    /*
     *  onPageScrollStateChanged(int): Interface de retorno de chamada para responder ao estado de
     *      mudança da página selecionada.
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
