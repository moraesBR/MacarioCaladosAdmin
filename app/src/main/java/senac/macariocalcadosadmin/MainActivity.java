package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import senac.macariocalcadosadmin.adapters.ViewPagerAdapter;
import senac.macariocalcadosadmin.firebase.Conexao;
import senac.macariocalcadosadmin.firebase.Database;
import senac.macariocalcadosadmin.fragments.BuscarFragment;
import senac.macariocalcadosadmin.fragments.InserirFragment;
import senac.macariocalcadosadmin.fragments.SapatoSettingsFragment;
import senac.macariocalcadosadmin.fragments.VisualizarFragment;
import senac.macariocalcadosadmin.models.SelecaoSapato;
import senac.macariocalcadosadmin.view.SettingsSapato;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;

    public static  List<SelecaoSapato> listaSapatos = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Inititestealization
        getNotification();

        database = new Database(this, "sapatos");

        bindView();
        setView();
    }

    private void getNotification(){

        SharedPreferences sharedPreferences = getSharedPreferences(
                getResources().getString(R.string.pref_data),
                MODE_PRIVATE);
        boolean flag   = sharedPreferences.getBoolean(getResources().getString(R.string.pref_notification),false);
        if (flag){

            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
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
        listaSapatos = savedInstanceState.getParcelableArrayList(LIST_SHOES_STATE);
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar_main);
        bottomNavigationView = findViewById(R.id.bn_menu);
        viewPager = findViewById(R.id.vp_fragments);
    }

    private void setView() {
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        viewPager.addOnPageChangeListener(MainActivity.this);
        setViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        VisualizarFragment visualizarFragment = new VisualizarFragment();
        BuscarFragment buscarFragment = new BuscarFragment();
        InserirFragment inserirFragment = new InserirFragment();
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
                startActivity(new Intent(MainActivity.this, SettingsSapato.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    private void logout() {
        Conexao.logOut();
        //finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
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
