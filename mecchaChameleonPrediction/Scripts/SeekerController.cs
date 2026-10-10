using UnityEngine;

// 술래: 조준해서 클릭하면 숨는 사람을 잡는다. 헛클릭에는 페널티 (예상 구현)
public class SeekerController : MonoBehaviour
{
    [SerializeField] private Camera cam;
    [SerializeField] private float reach = 30f;
    [SerializeField] private float missPenaltySeconds = 3f;

    private float stunnedUntil;

    private void Update()
    {
        if (GameManager.Instance.Phase != GamePhase.Seek) return;
        if (Time.time < stunnedUntil) return;
        if (!Input.GetMouseButtonDown(0)) return;

        Ray ray = cam.ViewportPointToRay(new Vector3(0.5f, 0.5f, 0f));
        if (Physics.Raycast(ray, out RaycastHit hit, reach) &&
            hit.collider.TryGetComponent(out HiderState hider) && !hider.IsCaught)
        {
            GameManager.Instance.RequestCatch(this, hider); // 판정은 서버(호스트)에서
        }
        else
        {
            // 아무 데나 마구 클릭하는 걸 막기 위한 페널티
            stunnedUntil = Time.time + missPenaltySeconds;
        }
    }
}
