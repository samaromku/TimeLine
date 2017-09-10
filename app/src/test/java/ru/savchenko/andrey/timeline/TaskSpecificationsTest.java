package ru.savchenko.andrey.timeline;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;
import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "ru.savchenko.andrey.timeline", manifest = Config.NONE)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmConfiguration.class, RealmQuery.class, RealmResults.class, RealmCore.class, RealmLog.class})
public class TaskSpecificationsTest {
    PlayersSpec playersSpec;
    @Rule
    public PowerMockRule rule = new PowerMockRule();


    private Realm mockRealm;
    private Player player = new Player(12, "test", 123123, 1);

    @Before
    public void setup() throws Exception {
        mockStatic(RealmCore.class);
        mockStatic(RealmLog.class);
        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        Realm.init(RuntimeEnvironment.application);

        final Realm mockRealm = mock(Realm.class);
        final RealmConfiguration mockRealmConfig = mock(RealmConfiguration.class);

        doNothing().when(RealmCore.class);
        RealmCore.loadLibrary(any(Context.class));

        playersSpec = new PlayersSpec();

        whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        this.mockRealm = mockRealm;
    }

    private void checkTransactionCount(int count) {
        verify(mockRealm, times(count)).executeTransaction(Mockito.any(Realm.Transaction.class));
        verify(mockRealm, times(count)).close();
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }

    @Test
    public void addPlayers() throws Exception {
        playersSpec.addPlayers(new ArrayList<>());
        playersSpec.addPlayers(null);
        checkTransactionCount(1);
    }

    @Test
    public void getPlayers() throws Exception {
        List<Player>players = new ArrayList<>();
        players.add(player);
        playersSpec.addPlayers(players);
        System.out.println(playersSpec.getPlayers());
        System.out.println(playersSpec.getPlayerByPosition(12));

    }

    @Test
    public void getPlayerByPosition() throws Exception {

    }

}