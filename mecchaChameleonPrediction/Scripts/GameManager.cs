using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public enum GamePhase { Lobby, Paint, Seek, Result }

// 라운드 진행: 로비 -> 칠하기(술래는 눈 가림) -> 찾기 -> 결과 (예상 구현)
// 실제 게임은 Steam 로비 + 호스트 권한 방식의 P2P 멀티플레이일 가능성이 높다고 본다.
public class GameManager : MonoBehaviour
{
    public static GameManager Instance { get; private set; }

    [SerializeField] private float paintTime = 90f;
    [SerializeField] private float seekTime = 180f;
    [SerializeField] private float resultTime = 10f;

    public GamePhase Phase { get; private set; } = GamePhase.Lobby;
    public float TimeLeft { get; private set; }

    private readonly List<HiderState> hiders = new List<HiderState>();
    private readonly List<SeekerController> seekers = new List<SeekerController>();

    private void Awake() => Instance = this;

    public void StartRound(List<HiderState> roundHiders, List<SeekerController> roundSeekers)
    {
        hiders.Clear(); hiders.AddRange(roundHiders);
        seekers.Clear(); seekers.AddRange(roundSeekers);
        SetPhase(GamePhase.Paint, paintTime);
        seekers.ForEach(s => Blindfold.Show(s)); // 술래는 칠하는 동안 화면 가림
    }

    private void Update()
    {
        if (Phase == GamePhase.Lobby) return;

        TimeLeft -= Time.deltaTime;
        if (TimeLeft > 0f) return;

        switch (Phase)
        {
            case GamePhase.Paint:
                seekers.ForEach(s => Blindfold.Hide(s));
                SetPhase(GamePhase.Seek, seekTime);
                break;
            case GamePhase.Seek:
                EndRound(hidersWin: true); // 시간 끝까지 버티면 숨는 쪽 승리
                break;
            case GamePhase.Result:
                Phase = GamePhase.Lobby;
                break;
        }
    }

    public void RequestCatch(SeekerController seeker, HiderState hider)
    {
        if (Phase != GamePhase.Seek || hider.IsCaught) return;
        hider.Catch();
        if (hiders.All(h => h.IsCaught))
            EndRound(hidersWin: false);
    }

    private void EndRound(bool hidersWin)
    {
        SetPhase(GamePhase.Result, resultTime);
        // 가장 오래 버틴 사람이 MVP
        HiderState mvp = hiders.OrderByDescending(h => h.SurvivedSeconds).FirstOrDefault();
        ResultUI.Show(hidersWin, mvp);
    }

    private void SetPhase(GamePhase phase, float duration)
    {
        Phase = phase;
        TimeLeft = duration;
    }
}
