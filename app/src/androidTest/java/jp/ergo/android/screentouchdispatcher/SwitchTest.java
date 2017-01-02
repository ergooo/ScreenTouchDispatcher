package jp.ergo.android.screentouchdispatcher;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class SwitchTest {
    private Context context() {
        return InstrumentationRegistry.getContext();
    }

    @Test
    public void isCheckedの初期値はfalse() {
        final Switch sut = new Switch(context());
        assertThat(sut.isChecked(), is(false));
    }

    @Test
    public void クリックするとisCheckedがtrueになる() {
        final Switch sut = new Switch(context());
        sut.performClick();
        assertThat(sut.isChecked(), is(true));
    }

    @Test
    public void クリックされて値が変わるとリスナが呼ばれる() {
        final Switch sut = new Switch(context());

        final Function1 mockListener = mock(Function1.class);
        sut.setOnCheckChanged(mockListener);
        sut.performClick();
        verify(mockListener, times(1)).invoke(anyBoolean());
    }

    @Test
    public void falseのときに値が変わるとリスナにはtrueを渡す() {
        final Switch sut = new Switch(context());
        assertThat(sut.isChecked(), is(false));

        sut.setOnCheckChanged(new Function1<Boolean, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean) {
                assertThat(aBoolean, is(true));
                return Unit.INSTANCE;
            }
        });
        sut.performClick();
    }

    @Test
    public void setCheckedで値が変わる() {
        final Switch sut = new Switch(context());
        assertThat(sut.isChecked(), is(false));
        sut.setChecked(true);
        assertThat(sut.isChecked(), is(true));
        sut.setChecked(false);
        assertThat(sut.isChecked(), is(false));
    }
}