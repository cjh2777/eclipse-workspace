using UnityEngine;

public class HiderState : MonoBehaviour
{
    public bool IsCaught { get; private set; }
    public float SurvivedSeconds { get; private set; }

    private void Update()
    {
        if (!IsCaught && GameManager.Instance.Phase == GamePhase.Seek)
            SurvivedSeconds += Time.deltaTime;
    }

    public void Catch()
    {
        IsCaught = true;
        // 잡히면 관전 모드로 전환
        GetComponent<HiderController>().enabled = false;
        SpectatorCamera.Enter();
    }
}
