## TODOs
- user dict no element
- menu
- storageuser
- and service
- add boradcastreceiver

## Permissions

- define P in app A.
  - protection level: dangerous, normal
- used by another component in the same app
- used by another component in another app
  - request
    - denied
      - never show -> system-grant-revoke -> work (same as "can show")
      - can show
    - granted
      - never show (impossible)
      -> revoked (have to re-grant)

- deal with temporarily granted perm (content provider)
- permission filter for intent
